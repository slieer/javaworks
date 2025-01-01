package com.broqs.thrift.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class XcapServiceImpl implements XcapService {
	private String address = "localhost";
	private int port = 7911;
	private XcapOp.Client client = null;

	public XcapServiceImpl() {
		TTransport transport = new TSocket(address, port);
		TProtocol protocol = new TBinaryProtocol(transport);
		client = new XcapOp.Client(protocol);
	}

	public String get(String app, String docUrl, String token)
			throws TException {
		return client.get(app, docUrl, token);
	}

	public String put(String app, String docUrl, String xmldoc, String token)
			throws TException {
		return client.put(app, docUrl, xmldoc, token);
	}

	public String _del(String app, String docUrl, String token)
			throws TException {
		return client._del(app, docUrl, token);
	}

	public static void main(String[] args) {
		XcapService service = new XcapServiceImpl();
		String token = "";
		String docUrl = null;
		try {
			service.get(XcapService.FRIENT_LIST_APP, docUrl, token);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
