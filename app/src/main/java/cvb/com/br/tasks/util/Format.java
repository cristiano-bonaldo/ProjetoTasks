package cvb.com.br.tasks.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static String formatBoolean(int valor) {
        return (valor == 0 ? "false" : "true");
    }

    public static String formatDate(Date data, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(data.getTime());
    }
}
