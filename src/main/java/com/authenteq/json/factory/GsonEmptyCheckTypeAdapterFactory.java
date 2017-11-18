package com.authenteq.json.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Change empty Maps to null
 * Source http://chrisjenx.com/gson-empty-json-to-null/
 */
public class GsonEmptyCheckTypeAdapterFactory implements TypeAdapterFactory
{
	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
		// We filter out the EmptyCheckTypeAdapter as we need to check this for emptiness!
		if (Map.class.isAssignableFrom( type.getRawType() )) {
			final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
			final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
			return new EmptyCheckTypeAdapter<>(delegate, elementAdapter).nullSafe();
		}
		return null;
	}

	static public class EmptyCheckTypeAdapter<T> extends TypeAdapter<T>
	{
		private final TypeAdapter<T> delegate;
		private final TypeAdapter<JsonElement> elementAdapter;

		public EmptyCheckTypeAdapter( final TypeAdapter<T> delegate, final TypeAdapter<JsonElement> elementAdapter )
		{
			this.delegate = delegate;
			this.elementAdapter = elementAdapter;
		}

		@Override
		public void write( final JsonWriter out, final T value ) throws IOException
		{
			this.delegate.write( out, value );
		}

		@Override
		public T read( final JsonReader in ) throws IOException
		{
			final JsonObject asJsonObject = elementAdapter.read( in ).getAsJsonObject();
			if( asJsonObject.entrySet().isEmpty() )
				return null;
			return this.delegate.fromJsonTree( asJsonObject );
		}
	}
}