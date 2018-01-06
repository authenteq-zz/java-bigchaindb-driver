package com.authenteq.json.strategy;

import com.authenteq.model.Transaction;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TransactionIdExclusionStrategy implements ExclusionStrategy
{
	public boolean shouldSkipClass( Class<?> klass )
	{
		return false;
	}

	public boolean shouldSkipField( FieldAttributes f )
	{
		return f.getDeclaringClass() == Transaction.class && f.getName().equals( "id" );
	}
}
