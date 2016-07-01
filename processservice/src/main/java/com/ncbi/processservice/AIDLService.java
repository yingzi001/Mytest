package com.ncbi.processservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLService extends Service {
    class AIDLInterface extends IMyAidlInterface.Stub {


        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    }

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AIDLInterface();
    }
    
}
