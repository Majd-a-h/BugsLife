import java.util.*;
public class UndoRedoStack_Comment extends Stack<String> {
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    public void push_to_undoStack(String item) {undoStack.push(item);}
    public String pop_from_undoStack() {return undoStack.pop();}
    public String peek_from_undoStack() {return undoStack.peek();}
    public boolean is_empty_undoStack() {return undoStack.isEmpty();}
    public int size_undoStack(){return undoStack.size();}

    public void push_to_redoStack(String item) {redoStack.push(item);}
    public String pop_from_redoStack() {return redoStack.pop();}
    public String peek_from_redoStack() {return redoStack.peek();}
    public boolean is_empty_redoStack() {return redoStack.isEmpty();}
    public int size_redoStack(){return redoStack.size();}
} //end of the class