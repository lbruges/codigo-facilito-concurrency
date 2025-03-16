import com.codigofacilito.common.props.reader.GlobalProperties;

public class Main {
    public static void main(String[] args) {
        var matrixProps = GlobalProperties.getInstance().getMatrixProperties();
        var backtrack = GlobalProperties.getInstance().getBacktrackerProperties();
        System.out.println(matrixProps);
        System.out.println(backtrack);
    }
}