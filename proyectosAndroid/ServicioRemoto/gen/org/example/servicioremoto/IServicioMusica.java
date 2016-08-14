/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\eclipse\\workspace\\ServicioRemoto\\src\\org\\example\\servicioremoto\\IServicioMusica.aidl
 */
package org.example.servicioremoto;
public interface IServicioMusica extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.example.servicioremoto.IServicioMusica
{
private static final java.lang.String DESCRIPTOR = "org.example.servicioremoto.IServicioMusica";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.example.servicioremoto.IServicioMusica interface,
 * generating a proxy if needed.
 */
public static org.example.servicioremoto.IServicioMusica asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.example.servicioremoto.IServicioMusica))) {
return ((org.example.servicioremoto.IServicioMusica)iin);
}
return new org.example.servicioremoto.IServicioMusica.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_reproduce:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.reproduce(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setPosicion:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setPosicion(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getPosicion:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPosicion();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.example.servicioremoto.IServicioMusica
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public java.lang.String reproduce(java.lang.String mensaje) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(mensaje);
mRemote.transact(Stub.TRANSACTION_reproduce, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setPosicion(int ms) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ms);
mRemote.transact(Stub.TRANSACTION_setPosicion, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getPosicion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPosicion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_reproduce = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setPosicion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getPosicion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public java.lang.String reproduce(java.lang.String mensaje) throws android.os.RemoteException;
public void setPosicion(int ms) throws android.os.RemoteException;
public int getPosicion() throws android.os.RemoteException;
}