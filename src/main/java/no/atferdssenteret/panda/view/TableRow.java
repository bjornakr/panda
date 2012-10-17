package no.atferdssenteret.panda.view;

import no.atferdssenteret.panda.model.Model;

public class TableRow {
    private final Object[] columns;
    private Model metaData = null; // Could be the object the row is representing, or maybe just an ID - or nothing at all.
    
//    public TableRow(Object[] columns) {
//	this.columns = columns;
//    }
    
    public TableRow(Object[] columns, Model metaData) {
	this.columns = columns;
	this.metaData = metaData;
    }

    public Object[] getColumns() {
        return columns;
    }

    public Model getMetaData() {
        return metaData;
    }
    
    @Override
    public boolean equals(Object o) {
	if (o instanceof TableRow) {
	    return ((TableRow)o).metaData.equals(metaData);
	}
	return false;
    }
    
    @Override
    public int hashCode() {
	return metaData.hashCode();
    }
}
