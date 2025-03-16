package com.codigofacilito.common.props.model;

import java.util.Properties;

import static com.codigofacilito.common.props.util.PropsUtils.PROPS_SEPARATOR;
import static com.codigofacilito.common.props.util.PropsUtils.readIntegerProperty;

public class ScoreProperties implements PropsWithPrefix {

    private final int gapScore;
    private final int matchScore;
    private final int missScore;

    private static final String PROPS_PREFIX = "score";

    public ScoreProperties(int gapScore, int matchScore, int missScore) {
        this.gapScore = gapScore;
        this.matchScore = matchScore;
        this.missScore = missScore;
    }

    public ScoreProperties(ScoringPropsBuilder builder) {
        this.gapScore = builder.gapScore;
        this.matchScore = builder.matchScore;
        this.missScore = builder.missScore;
    }

    @Override
    public String getPrefix() {
        return PROPS_PREFIX;
    }

    public static ScoringPropsBuilder builder() {
        return new ScoringPropsBuilder();
    }

    public int getGapScore() {
        return gapScore;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public int getMissScore() {
        return missScore;
    }

    @Override
    public String toString() {
        return "ScoreProperties{" +
                "gapScore=" + gapScore +
                ", matchScore=" + matchScore +
                ", missScore=" + missScore +
                '}';
    }

    public static class ScoringPropsBuilder {

        private String basePath;
        private Properties properties;
        private int gapScore;
        private int matchScore;
        private int missScore;

        public ScoringPropsBuilder fromProperties(String parentPrefix, Properties properties)  {
            this.basePath = String.join(PROPS_SEPARATOR, parentPrefix, PROPS_PREFIX);
            this.properties = properties;
            return this;
        }

        public ScoringPropsBuilder orDefault(ScoreProperties defaultProps) {
            this.gapScore = defaultProps.gapScore;
            this.matchScore = defaultProps.matchScore;
            this.missScore = defaultProps.missScore;

            return this;
        }

        public ScoreProperties build() {
            gapScore = readIntegerProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "gap"), gapScore);
            matchScore = readIntegerProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "match"), matchScore);
            missScore = readIntegerProperty(properties, String.join(PROPS_SEPARATOR, basePath,
                    "miss"), missScore);

            return new ScoreProperties(this);
        }

    }

}

