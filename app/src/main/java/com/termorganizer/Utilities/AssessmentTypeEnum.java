package com.termorganizer.Utilities;

public enum AssessmentTypeEnum {
    PERFORMANCE{
        @Override
        public String toString() {
            return "Performance Assessment";
        }
    },
    OBJECTIVE{
        @Override
        public String toString() {
            return "Objective Assessment";
        }
    },
}
