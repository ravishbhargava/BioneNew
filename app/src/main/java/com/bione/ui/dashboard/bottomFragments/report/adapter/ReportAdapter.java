class LedModule {
    public int nativePtr = 0;
    private Callback callback;
  
    private void nativeRelease(int ptr);
    private void nativeInit(NativeCallback callback);
  
    void addInitListeners(Callback c) {
      callback = c;
    }
  
    void start() { // turn on LED
      nativeInit((ptr) -> { // after 5 sec
        nativePtr = ptr;
        callback.call();
      });
    }
  
    void stop() { // Turn off LED
      if ( nativePtr != 0 ) {
        nativeRelease(nativePtr);
      }
    }
  }
  
  

  
  class Activity {
    LedModule h = null;
  
    void onResume() {
      if ( h != null ) {
        h.release();
      }
  
      h = new LedModule();
      h.addInitListeners(() -> {})
      h.start();
    }
  
    void onPause() {
      h.stop();
      h = null;
    }


    void onResume() {
        if ( h != null ) {
          h.release();
        }
    
        finak temp = new LedModule();
        h = temp;
        h.addListeners(() -> {
          if ( h != temp ) {
            temp.release();
          }
        })
        h.start();
      }


  }