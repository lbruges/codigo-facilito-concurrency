import utils.MatrixInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------- Sequence Alignment! --------");
        MatrixInitializer matrixInitializer = MatrixInitializer.builder()
                .chainA("ACG")
                .chainB("ATCG")
                .gapScore(-2)
                .missScore(-1)
                .matchScore(1)
                .withInitialGapValues()
                .build();

        matrixInitializer.printMatrix();
    }
}