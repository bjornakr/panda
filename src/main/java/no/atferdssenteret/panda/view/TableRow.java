package no.atferdssenteret.panda.view;

public class TableRow {
    private final Object[] columns;
    private Object metaData = null; // Could be the object the row is representing, or maybe just an ID - or nothing at all.
    
    public TableRow(Object[] columns) {
	this.columns = columns;
    }
    
    public TableRow(Object[] columns, Object metaData) {
	this.columns = columns;
	this.metaData = metaData;
    }

    public Object[] getColumns() {
        return columns;
    }

    public Object getMetaData() {
        return metaData;
    }
}
