package io.mentusa.whouses.psi;

public class FormatTool {
    private static final int targetLength = 35;

    public static String formatClassName(String fqClassName) {
        int inLen = fqClassName.length();
        if (inLen < targetLength) {
            return fqClassName;
        }
        StringBuilder buf = new StringBuilder(inLen);
        int rightMostDotIndex = fqClassName.lastIndexOf('.');
        if (rightMostDotIndex == -1) {
            return fqClassName;
        }
        int lastSegmentLength = inLen - rightMostDotIndex;
        int leftSegments_TargetLen = Math.max(0, targetLength - lastSegmentLength);
        int leftSegmentsLen = inLen - lastSegmentLength;
        int maxPossibleTrim = leftSegmentsLen - leftSegments_TargetLen;
        int trimmed = 0;
        boolean inDotState = true;
        int i = 0;
        for (; i < rightMostDotIndex; i++) {
            char c = fqClassName.charAt(i);
            if (c == '.') {
                if (trimmed >= maxPossibleTrim)
                    break;
                buf.append(c);
                inDotState = true;
            } else if (inDotState) {
                buf.append(c);
                inDotState = false;
            } else {
                trimmed++;
            }
        }
        buf.append(fqClassName.substring(i));
        return buf.toString();
    }
}
