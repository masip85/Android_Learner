package org.example.puntuacionesprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class PuntuacionesProvider extends ContentProvider {

	public static final String AUTORIDAD = "org.example.puntuacionesprovider";
	public static final Uri CONTENT_URI = Uri.parse("content//"+AUTORIDAD+"/puntuaciones");
	public static final int TODOS_LOS_ELEMENTOS = 1;
	public static final int UN_ELEMENTO = 2;
	private static UriMatcher URI_MATCHER = null;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTORIDAD, "puntuaciones", TODOS_LOS_ELEMENTOS);
		URI_MATCHER.addURI(AUTORIDAD, "puntuaciones/#", UN_ELEMENTO);
	}
	public static final String TABLA = "puntuaciones";
	private SQLiteDatabase baseDeDatos;
	
	@Override
	public boolean onCreate() {
		PuntuacionesSQLiteHelper db = new PuntuacionesSQLiteHelper(getContext());
		baseDeDatos = db.getWritableDatabase();
		return baseDeDatos != null && baseDeDatos.isOpen();
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (URI_MATCHER.match(uri)) {
		case TODOS_LOS_ELEMENTOS:
			break;
		case UN_ELEMENTO:
			String id = uri.getPathSegments().get(1);
			if(TextUtils.isEmpty(selection)) {
				selection = "_id = " + id;
			} else {
				selection = "_id = " + id + " AND (" + selection + ")";
			}
			break;
		default:
			throw new IllegalArgumentException("URI incorrecta: " + uri);
		}
		return baseDeDatos.delete(TABLA, selection, selectionArgs);
	}

	@Override
	public String getType(final Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case TODOS_LOS_ELEMENTOS:
			return "vnd.android.cursor.dir/vnd.org.example.puntuacion";
		case UN_ELEMENTO:
			return "vnd.android.cursor.item/vnd.org.example.puntuacion";
		default:
			throw new IllegalArgumentException("URI incorrecta: " + uri);
		}
		
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		long idFila = baseDeDatos.insert(TABLA, null, values);
		if (idFila > 0) {
			return ContentUris.withAppendedId(CONTENT_URI, idFila);
		} else {
			throw new SQLException("Error al insertar registro en " + uri);
		}
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] argSeleccion, String orden) {
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLA);
		
		switch (URI_MATCHER.match(uri)) {
		case TODOS_LOS_ELEMENTOS:
			break;
		case UN_ELEMENTO:
			String id = uri.getPathSegments().get(1);
			queryBuilder.appendWhere("_id = " + id);
			break;
		default:
			throw new IllegalArgumentException("URI incorrecta: " + uri);
		}
		return queryBuilder.query(baseDeDatos, projection, selection, argSeleccion, null, null, orden);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		switch (URI_MATCHER.match(uri)) {
		case TODOS_LOS_ELEMENTOS:
			break;
		case UN_ELEMENTO:
			String id = uri.getPathSegments().get(1);
			if(TextUtils.isEmpty(selection)) {
				selection = "_id = " + id;
			} else {
				selection = "_id = " + id + " AND (" + selection + ")";
			}
			break;
		default:
			throw new IllegalArgumentException("URI incorrecta: " + uri);
		}
		return baseDeDatos.update(TABLA, values, selection, selectionArgs);
	}

}
