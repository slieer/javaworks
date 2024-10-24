@SuppressWarnings("module")
module v1ch12.openpkg2
{
   requires jakarta.json.bind;
   opens com.horstmann.places to org.eclipse.yasson;
}
