package com.tmac.onsite.utils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import android.R.integer;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageLoader { 
	
	
	/**
	 * IamgeLoader的具体流程，外部调用loadImage方法，loadImage方法中有一个UIHandler接收图片消息、
	 * 首先从缓存中根据path去查询bitmao是否为空，不为空，发送UIHandler  更新UI 
	 * 若为空，则去创建一个Runnable ，在Runnable中去压缩图片，加入缓存，并且调用release方法去释放信号量、
	 * 将Runnable加入到线程池的TaskQuene中，通知线程池去执行线程，设置信号量的个数控制线程的同步，调用acquire方法
	 * （线程池任务队列的类型，FIFO，LIFO）
	 * */
	
	private final static String TAG = "ImageLoader";
	
	private static ImageLoader mInstance;
	
	/**
	 * imageview改变时回调通知的接口
	 */
	private OnNotifyListener mListener;
	
	/**
	 * 图片缓存的核心对象 
	 */
	private LruCache<String, Bitmap> mLruCache;

	/**
	 * 线程池
	 */
	private ExecutorService mThredPool;
	private static final int DEFAULT_THREAD_COUNT = 1;
	
	/**
	 *图片的调度方式 
	 */
	private Type mType = Type.LIFO;
	
	/**
	 *任务队列 
	 */
	private LinkedList<Runnable> mTaskQueue;
	/**
	 * 后台轮询线程
	 * */
	private Thread mPoolThread;
	private Handler mPoolThreadHandler;
	/**
	 * UI线程中Handle
	 */
	private Handler mUIHandler;
	/**
	 * 信号量
	 * */
	private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
	private Semaphore mSemaphoreThreadPool;
	
	public enum Type{
		FIFO,LIFO;
	}
	
	private ImageLoader( int threadCount, Type type){
		init(threadCount, type);
	}
	
	private void init(int threadCount,Type type){
		
		mPoolThread = new Thread(){
			
			@Override
			public void run() {
				Looper.prepare();
				mPoolThreadHandler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						//线程池去取出来一个任务进行执行
						mThredPool.execute(getTask());
						try {
							//信号量机制，每执行3个就阻塞
							Log.d(TAG, "mThreadPool execute");
							mSemaphoreThreadPool.acquire();
							Log.d(TAG, "mSemaphoreThreadPool acquire");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				//释放一个信号量
				mSemaphorePoolThreadHandler.release();
				Looper.loop();
				
			};
		};
		mPoolThread.start();
		//获取应用最大的可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheMemory = maxMemory/8;
		
		mLruCache = new LruCache<String, Bitmap>(cacheMemory){
			//测量每个bitmap的值
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				//每一个bitmap占据的字节数*他的高度
				return value.getRowBytes() * value.getHeight();
			}
			
		};
	    //创建线程池
		mThredPool = Executors.newFixedThreadPool(threadCount);
		mTaskQueue = new LinkedList<Runnable>();
		mType = type;
		
		mSemaphoreThreadPool = new Semaphore(threadCount);
		
	}
	
	/**
	 * 从任务队列取出取出一个方法
	 * */
	private Runnable getTask() {
		// TODO Auto-generated method stub
		if(mType == Type.FIFO){
			return mTaskQueue.removeFirst();
		}else if(mType == Type.LIFO){
			return mTaskQueue.removeLast();
		}
		return null;
	}
	
	public static ImageLoader getInstance(){
		return mInstance;
	}
	
	public static ImageLoader getInstance(int threadCount, Type type){
		
		if(mInstance == null){
			synchronized (ImageLoader.class) {
				if(mInstance == null){
					mInstance = new ImageLoader(threadCount, type);
				}
			}
			
		}
		return mInstance;
	}
	
	/**
	 * 根据path去加载图片
	 * 未完成的：参数中添加布尔值：是不是url网络的
	 * 
	 * */
	public void loadImage(final String path, final ImageView imageView){
		
		imageView.setTag(path);
		if(mUIHandler == null){
			mUIHandler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					//获取得到图片， 为imageView回调设置图片
					ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
					Bitmap bm = holder.bitmap;
					ImageView imageView = holder.imageView;
					String path = holder.path;
					//将path与getTag存储路径进行比较
					if(imageView.getTag().toString().equals(path)){
						imageView.setImageBitmap(bm);
						mListener.notifyView();
					}
				}
			};
		}
		
		//根据path在缓存中获取Bitmap
		final Bitmap bitmap = getBitmapFromLruCache(path);
		
		if(bitmap != null){
			refreshBitmap(path, imageView, bitmap);
		}else{
			addTasks(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//加载图片
					//图片的压缩
					//1.获得图片需要显示的大小
					Log.d(TAG, "new Runnable");
					ImageSize imageSize = getImageViewSize(imageView);
					//2.压缩图片
					Bitmap bm = decodeSampledBitmapFromPath(path,imageSize.width,imageSize.height);
					Log.d(TAG, "bm =" + bm);
					//3.那图片加入到缓存
					addBitmapToLruCache(path , bm);
					refreshBitmap(path, imageView, bitmap);
					mSemaphoreThreadPool.release();
				}
			});
		}
		
	}
	/**
	 * 向LruCache中添加bitmap
	 * */
	protected void addBitmapToLruCache(String path, Bitmap bm) {
		// TODO Auto-generated method stub
		if(getBitmapFromLruCache(path) == null){
			if(bm != null){
				mLruCache.put(path, bm);
			}
		}
	}
	
	/**
	 * 发送Handler，更新UI
	 * */
	private void refreshBitmap(final String path,
			final ImageView imageView, final Bitmap bitmap) {
		Message message = Message.obtain();
		ImageBeanHolder holder = new ImageBeanHolder();
		holder.bitmap = bitmap;
		holder.path = path;
		holder.imageView = imageView;
		message.obj = holder;
		mUIHandler.sendMessage(message);
	}

	/**
	 * 根据图片需要显示的宽和高进行压缩
	 * */
	protected Bitmap decodeSampledBitmapFromPath(String path, int width,
			int height) {
		// TODO Auto-generated method stub
		BitmapFactory.Options options = new BitmapFactory.Options();
		//不把图片加载到内存，只要图片的宽和高
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		
		options.inSampleSize = caculateInSampleSize(options, width, height);
		//使用获取到的inSampleSize再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	/**
	 * 根据需求的宽和高以及图片的宽和高实际计算SampleSize
	 * */
	private int caculateInSampleSize(Options options, int reqWidth, int reqHeight) {
		// TODO Auto-generated method stub
		int width = options.outWidth;
		int heigth = options.outHeight;
		
		int inSampleSize = 1;
		if(width > reqWidth || heigth > reqHeight){
			int widthRadio = Math.round(width * 1.0f/reqWidth);
			int heightRadio = Math.round(heigth * 1.0f/reqHeight);
			//取大值，节省内存，取小值，图片不失真
			inSampleSize = Math.max(widthRadio, heightRadio);
		}
		return inSampleSize;
	}

	/**
	 * 根据ImageView获取适当的压缩的高和宽
	 * 
	 * */
	protected ImageSize getImageViewSize(ImageView imageView) {
		// TODO Auto-generated method stub
		ImageSize imageSize = new ImageSize();
		DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
		
		LayoutParams lp = imageView.getLayoutParams();
		
//		int width = (lp.width == LayoutParams.WRAP_CONTENT? 0 : imageView.getWidth());
		int width = imageView.getWidth();
		if(width <= 0){
			width = lp.width;//获取imageView在Layout中声明的宽度
		}
		
		if(width <= 0){
//			width = imageView.getMaxWidth();
			width = getImageViewFieldValue(imageView, "mMaxWidth");//检查最大值
		}
		
		if(width <= 0){
			width = displayMetrics.widthPixels;
		}
		
		int height = imageView.getHeight();
		if(height <= 0){
			height = lp.height;//获取imageView在Layout中声明的宽度
		}
		
		if(height <= 0){
//			height = imageView.getMaxHeight();
			height = getImageViewFieldValue(imageView, "mMaxHeight");//检查最大值
		}
		
		if(height <= 0){
			height = displayMetrics.heightPixels;
		}
		imageSize.width = width;
		imageSize.height = height;
		return imageSize;
	}

	/**
	 * 通过反射获取imageView的某个属性值
	 * */
	private static int getImageViewFieldValue(Object object, String fieldName){
		
		int value = 0;
		
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if(fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
				value = fieldValue;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
		
	}
	
	private synchronized void addTasks(Runnable runnable) {
		// TODO Auto-generated method stub
		mTaskQueue.add(runnable);
		Log.d(TAG, "addTask");
		//判读 mPoolThreadHandler == null,解决空指针异常，并行执行
		try {
			if(mPoolThreadHandler == null){
				/**
				 * 此处acquire()方法，去请求，如果mPoolThreadHandlerme没有初始化完成，
				 * 则此处相当于wait(),等到初始化完成了，则会notify()。
				 * */
				mSemaphorePoolThreadHandler.acquire();
				Log.d(TAG, "PoolThreadHandler acquire");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPoolThreadHandler.sendEmptyMessage(0x110);
	}

	/*
	 * 根据path在缓存中获取bitmap
	 * */
	private Bitmap getBitmapFromLruCache(String key){
		
		return mLruCache.get(key);
		
	}
	
	
	private class ImageSize{
		int width;
		int height;
	}
	
	/**
	 * 为了避免Handle出现图片混乱
	 * 
	 * */
	private class ImageBeanHolder{
		
		Bitmap bitmap;
		ImageView imageView;
		String path;
		
	}
	
	public void setOnNotifyListener(OnNotifyListener listener){
		this.mListener = listener;
	}
	
	public interface OnNotifyListener{
		void notifyView();
	}
	
}
