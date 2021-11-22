package stepik.org.f;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
    public void start() {
        String text = "cvfdv=(pm!";
        String[] keywords = {"spam!","aaaaa"};
        TextAnalyzer[] analyzers = new TextAnalyzer[3];
        analyzers[0] = new SpamAnalyzer(keywords);
        analyzers[1] = new NegativeTextAnalyzer();
        analyzers[2] = new TooLongTextAnalyzer(50);
        System.out.println(checkLabels(analyzers, text));
    }
    public Label checkLabels(TextAnalyzer[] analyzers, String text) {
        Label label;
        for (TextAnalyzer analyzer: analyzers) {
            label = analyzer.processText(text);
            if (label != Label.OK) return label;
        }
        return Label.OK;
    }
    enum Label {
        SPAM, NEGATIVE_TEXT, TOO_LONG, OK
    }
    interface TextAnalyzer {
        Label processText(String text);
    }
    class NegativeTextAnalyzer extends KeywordAnalyzer implements TextAnalyzer {
        private final String[] keywords = {":(" , "=(", ":|"};
        public String[] getKeywords() {
            return this.keywords;
        }
        public Label getLabel(){
            return Label.NEGATIVE_TEXT;
        }
    }
    class TooLongTextAnalyzer implements TextAnalyzer {
        private int maxLength;

        public TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        public Label processText(String text) {
            return (text.length() > maxLength)? Label.TOO_LONG: Label.OK;
        }
    }
    public abstract class KeywordAnalyzer implements TextAnalyzer {
        protected abstract String[] getKeywords();
        protected abstract Label getLabel();
        public Label processText(String text) {
            String[] keywords = getKeywords();
            Label label = getLabel();
            for (String str : keywords) if(text.toLowerCase().contains(str)) return label;
            return Label.OK;
        }
    }
    class SpamAnalyzer extends KeywordAnalyzer implements TextAnalyzer {
        private String[] keywords;

        public SpamAnalyzer(String[] keywords) {
            this.keywords = keywords;
        }
        public String[] getKeywords() {
            return this.keywords;
        }
        public Label getLabel(){
            return Label.SPAM;
        }
    }
}
