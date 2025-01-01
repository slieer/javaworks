package com.broqs.thrift.client;

import org.apache.thrift.TException;

public interface XcapService {
	String FRIENT_LIST_APP = "";
	String PRESENCE_APP = "";

	String get(String app, String docUrl, String token) throws TException;

	String put(String app, String docUrl, String xmldoc, String token) throws TException;

	String _del(String app, String docUrl, String token) throws TException;

}
