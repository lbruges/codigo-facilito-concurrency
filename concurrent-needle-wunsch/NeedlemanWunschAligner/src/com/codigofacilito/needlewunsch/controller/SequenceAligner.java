package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.AlignedSequences;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

public interface SequenceAligner {

    AlignedSequences alignSequences(MatrixInfo matrixInfo);

}
