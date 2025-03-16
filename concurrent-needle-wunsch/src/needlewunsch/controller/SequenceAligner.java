package needlewunsch.controller;

import needlewunsch.models.AlignedSequences;
import needlewunsch.models.MatrixInfo;

public interface SequenceAligner {

    AlignedSequences alignSequences(MatrixInfo matrixInfo);

}
