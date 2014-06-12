package utilities;

import java.util.Vector;

import javax.swing.JTable;

public class GUIHandler {

	public int getLstEmptyTblRow(JTable tbl){
		for(int iii = 0; iii < tbl.getRowCount(); iii++){
			if(isRowEmpty(tbl, iii)){
				return iii;
			}
		}
		return -1;
	}
	
	public Vector<String> getTablesRowValues(JTable tbl, int rowNum){
		Vector<String> outVals = new Vector<String>();
		for(int iii = 0; iii < tbl.getColumnCount(); iii++){
			if(tbl.getValueAt(rowNum, iii) == null){
				outVals.add("");
			}else{
				outVals.add(tbl.getValueAt(rowNum, iii).toString());
			}
		}
		return outVals;
	}
	
	public Vector<String> getTablesValues(JTable tbl){
		Vector<String> outLns = new Vector<String>();
		String rowLn = "";
		
		int lstRowNum = this.getLstEmptyTblRow(tbl);
		if(lstRowNum == -1){
			lstRowNum = tbl.getRowCount();
		}
		
		Vector<String> rowVals = new Vector<String>();
		for(int iii = 0; iii < lstRowNum; iii++){
			rowLn = "";
			rowVals.clear();
			rowVals.addAll(getTablesRowValues(tbl, iii));
			
			for(String val: rowVals){
				rowLn = rowLn + "|" + val;
			}
			rowLn = rowLn.replaceFirst("\\|", "");
			outLns.add(rowLn);
		}
		return outLns;
	}

	public boolean isRowEmpty(JTable tbl, int rowNum){
		for(int iii = 0; iii < tbl.getColumnCount(); iii++){
			if(tbl.getValueAt(rowNum, iii) != null){
				if(!tbl.getValueAt(rowNum, iii).toString().equalsIgnoreCase("")){
					return false;
				}
			}
		}
		return true;
	}
	
}
