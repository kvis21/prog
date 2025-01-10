package utils;

import java.util.ArrayList;
import java.util.Objects;


public class Story {
    private ArrayList<String> sentences;

    public Story() {
        this.sentences = new ArrayList<String>();
    }

    public void tell(String[] sentences){
        for (String sentence : sentences) {
            this.sentences.add(sentence);
            System.out.println(sentence);
        }
    }

    public ArrayList<String> getSentences(){
        return this.sentences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return sentences == story.getSentences();
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentences);
    }


    @Override
    public String toString(){
        return  String.format("Story{sentences = %s}", sentences);
        }

}
