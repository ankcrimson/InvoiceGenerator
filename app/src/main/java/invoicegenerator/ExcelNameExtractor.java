package invoicegenerator;

import java.util.List;

public interface ExcelNameExtractor {
    List<NameAmountPair> getNameAmountPairs(String excelFileLocation);
}
