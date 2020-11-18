package com.example.aidl1;

public interface IMyAidlInterface extends android.os.IInterface {
    /**
     * Default implementation for IMyAidlInterface.
     */
    public static class Default implements com.example.aidl1.IMyAidlInterface {
        @Override
        public java.util.List<com.example.aidl1.Person> addPerson(com.example.aidl1.Person person) throws android.os.RemoteException {
            return null;
        }

        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements com.example.aidl1.IMyAidlInterface {
        private static final java.lang.String DESCRIPTOR = "com.example.aidl1.IMyAidlInterface";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.example.aidl1.IMyAidlInterface interface,
         * generating a proxy if needed.
         */
        public static com.example.aidl1.IMyAidlInterface asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.example.aidl1.IMyAidlInterface))) {
                return ((com.example.aidl1.IMyAidlInterface) iin);
            }
            return new com.example.aidl1.IMyAidlInterface.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_addPerson: {
                    data.enforceInterface(descriptor);
                    com.example.aidl1.Person _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.example.aidl1.Person.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    java.util.List<com.example.aidl1.Person> _result = this.addPerson(_arg0);
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements com.example.aidl1.IMyAidlInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public java.util.List<com.example.aidl1.Person> addPerson(com.example.aidl1.Person person) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<com.example.aidl1.Person> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((person != null)) {
                        _data.writeInt(1);
                        person.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_addPerson, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().addPerson(person);
                    }
                    _reply.readException();
                    _result = _reply.createTypedArrayList(com.example.aidl1.Person.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static com.example.aidl1.IMyAidlInterface sDefaultImpl;
        }

        static final int TRANSACTION_addPerson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

        public static boolean setDefaultImpl(com.example.aidl1.IMyAidlInterface impl) {
            // Only one user of this interface can use this function
            // at a time. This is a heuristic to detect if two different
            // users in the same process use this function.
            if (Stub.Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static com.example.aidl1.IMyAidlInterface getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public java.util.List<com.example.aidl1.Person> addPerson(com.example.aidl1.Person person) throws android.os.RemoteException;
}
