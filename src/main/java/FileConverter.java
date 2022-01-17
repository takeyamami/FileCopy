
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileConverter {

    private static String TARGET_FOLDER = "src/main/resources/csv";
    private static String OUTPUT_FOLDER = "src/main/resources/tmp";
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(String[] arg) throws IOException {
        Files.createDirectory(Paths.get(OUTPUT_FOLDER));

        // 一時フォルダにcsvディレクトリの中身をコピー
        Path path = Paths.get(TARGET_FOLDER);
        File[] files = path.toFile().listFiles();

        LocalDate now = LocalDate.now();
        for (int i = 0; i < files.length; i++) {

            FileWriter fw = new FileWriter(OUTPUT_FOLDER + "/" + files[i].getName());
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            try (BufferedReader br = new BufferedReader(new FileReader(files[i]))) {
                String readLine;
                while ((readLine = br.readLine()) != null) {
                    // 一時フォルダのファイルを文字列置換
                    String replaceText = readLine.replaceAll("today", now.format(FORMATTER))
                            .replaceAll("other_day", now.plusDays(1L).format(FORMATTER))
                            .replaceAll("future", now.plusYears(1L).format(FORMATTER));
                    pw.println(replaceText);
                }
            }
            // ファイル作成完了
            pw.close();
        }




        // TODO 今回は実装しない
        // てすとがある
        // 一時フォルダを削除
    }
}
