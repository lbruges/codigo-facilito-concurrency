package com.codigofacilito.needlewunsch.controller;

import com.codigofacilito.needlewunsch.models.AlignedSequences;
import com.codigofacilito.needlewunsch.models.MatrixInfo;

/**
 * Aligns sequences based on the calculated scoring matrix.
 */
public interface SequenceAligner {

    AlignedSequences alignSequences(MatrixInfo matrixInfo);

}
