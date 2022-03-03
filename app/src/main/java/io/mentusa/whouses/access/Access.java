package io.mentusa.whouses.access;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Access {
    private final long accessed;
    private final long accessor;
    private final AccessType type;
    private final int line;
}
