service XcapOp{
	string  get(1:string app,2:string docUrl,3:string token),
	string  put(1:string app,2:string docUrl,3:string xmldoc,4:string token),
	string _del(1:string app,2:string docUrl,3:string token)
}