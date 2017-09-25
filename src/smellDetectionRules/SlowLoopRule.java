package smellDetectionRules;

import beans.ClassBean;
import org.eclipse.jdt.core.dom.CompilationUnit;
import parser.CodeParser;
import parser.ForStatementVisitor;

import java.io.IOException;

public class SlowLoopRule {

    public boolean isSlowLoop(ClassBean pClassBean) throws IOException {
        CodeParser parser = new CodeParser();
        CompilationUnit compilationUnit = parser.createParser(pClassBean.getTextContent());
        ForStatementVisitor forVisitor = new ForStatementVisitor();

        compilationUnit.accept(forVisitor);

        return !forVisitor.getForStatements().isEmpty();
    }
}
