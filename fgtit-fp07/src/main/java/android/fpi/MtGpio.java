package android.fpi;


import android.util.Log;

public class MtGpio {
	
	private boolean mOpen = false;
	public MtGpio() {
		mOpen = openDev()>=0?true:false;
		Log.d("MtGpio","openDev->ret:"+mOpen);
	}
	
	public void BCPowerSwitch(boolean bonoff){
		if(bonoff){
			//Barcode Power
			sGpioMode(105,0);
			sGpioDir(105,1);
			sGpioOut(105,1);			
		}else{
			sGpioMode(105,0);
	    	sGpioDir(105,1);
	    	sGpioOut(105,0);
		}
	}
	
	public void BCReadSwitch(boolean bonoff){
		if(bonoff){
			//Barcode TRIG
			sGpioMode(106,0);
			sGpioDir(106,1);
			sGpioOut(106,1);			
		}else{
			sGpioMode(106,0);
	    	sGpioDir(106,1);
	    	sGpioOut(106,0);
		}
	}
	
	public void FPPowerSwitch(boolean bonoff){
		if(bonoff){
			//FP Power
			sGpioMode(119,0);
			sGpioDir(119,1);
			sGpioOut(119,1);
		}else{
			sGpioMode(119,0);
	    	sGpioDir(119,1);
	    	sGpioOut(119,0);
		}
	}
	
	public void RFPowerSwitch(boolean bonoff){
		if(bonoff){
			//RFID 1.8V Reset
			sGpioMode(20,0);
			sGpioDir(20,1);
			sGpioOut(20,1);
	        
			//RFID M
			sGpioMode(19,0);
			sGpioDir(19,1);
			sGpioOut(19,0);
			
			//RF EN
			sGpioMode(107,0);
			sGpioDir(107,1);
			sGpioOut(107,1);
		}else{
			sGpioMode(20,0);
			sGpioDir(20,1);
			sGpioOut(20,0);
	        
			sGpioMode(19,0);
			sGpioDir(19,1);
			sGpioOut(19,0);
			
			sGpioMode(107,0);
			sGpioDir(107,1);
			sGpioOut(107,0);
		}
	}
	
	public void RFInit(){	//13.56MHZ
		sGpioMode(12,0);	//RF_RES
		sGpioDir(12,1);
		sGpioOut(12,1);
		
		sGpioMode(80,0);	//RF_CS
		sGpioDir(80,1);
		sGpioOut(80,1);
		
		sGpioMode(81,0);	//RF_SCLK
		sGpioDir(81,1);
		sGpioOut(81,0);
		
		sGpioMode(83,0);	//RF_MOSI
		sGpioDir(83,1);
		sGpioOut(83,0);
		
		sGpioMode(82,0);	//RF_MISO
		sGpioDir(82,0);
	}
	
	public void RFReset(int reset){
		//sGpioMode(12,0);
		//sGpioDir(12,1);
		sGpioOut(12,reset);
	}
	
	public void RFCS(int cs){
		//sGpioMode(80,0);
		//sGpioDir(80,1);
		sGpioOut(80,cs);
	}
	
	public void RFCLK(int clk){
		//sGpioMode(81,0);
		//sGpioDir(81,1);
		sGpioOut(81,clk);
	}
	
	public void RFSet(int sta){
		//sGpioMode(83,0);
		//sGpioDir(83,1);
		sGpioOut(83,sta);
	}
	
	public int RFGet(){
		//sGpioMode(82,0);
		//sGpioDir(82,0);
		return getGpioIn(82);
	}
	
	public boolean isOpen(){
		return mOpen;
	}
	
	public void sGpioDir(int pin, int dir){
		setGpioDir(pin,dir);
	}
	
	public void sGpioOut(int pin, int out){
		setGpioOut(pin,out);
	}
	
	public int getGpioPinState(int pin){
		return getGpioIn(pin);
	}
	
	public void sGpioMode(int pin, int mode){
		setGpioMode(pin, mode);
	}
	
	// JNI
	private native int openDev();
	public native void closeDev();
	private native int setGpioMode(int pin, int mode);
	private native int setGpioDir(int pin, int dir);////璁剧疆杈撳叆杈撳嚭鏂瑰悜
	private native int setGpioPullEnable(int pin, int enable);
	private native int setGpioPullSelect(int pin, int select);
	public native  int setGpioOut(int pin, int out);//杈撳嚭楂樹綆鐢靛�?
	private native int getGpioIn(int pin); //鑾峰緱PIN鐘舵�?
	static {
		System.loadLibrary("mtgpio");
	}
}
