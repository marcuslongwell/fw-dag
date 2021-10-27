package dag.fw;

public class Vertex {
    private long id;
    private char letter;

    public Vertex(long id) {
        setId(id);
    }

    public Vertex(long id, char letter) {
        setId(id);
        setLetter(letter);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public boolean equals(Vertex vertex) {
        return this.getId() == vertex.getId();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vertex ");
        if (getLetter() == 0) {
            stringBuilder.append(getId());
        } else {
            stringBuilder.append(getLetter());
        }
        return stringBuilder.toString();
    }
}
