package invoicegenerator;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ExcelNameExtractorImpl implements ExcelNameExtractor {
    String getValueAsString(Cell cell){
        if(cell.getCellType() == CellType.STRING){
            return cell.getStringCellValue();
        }
        if(cell.getCellType() == CellType.NUMERIC){
            return NumberFormat.getCurrencyInstance(Locale.US).format(cell.getNumericCellValue());
        }
        return "";
    }

    @Override
    public List<NameAmountPair> getNameAmountPairs(String excelFileLocation) {
        List<NameAmountPair> nameAmountPairs=new LinkedList<>();
        try (FileInputStream file = new FileInputStream(excelFileLocation)){
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String name = getValueAsString(row.getCell(0));
                String amount = getValueAsString(row.getCell(1));
                if(!"Name".equalsIgnoreCase(name) && StringUtil.isNotBlank(name)) {
                    nameAmountPairs.add(new NameAmountPair(name, amount));
                }
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        return nameAmountPairs;
    }
}
