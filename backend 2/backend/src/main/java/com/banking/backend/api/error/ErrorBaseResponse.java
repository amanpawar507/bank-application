package com.banking.backend.api.error;

import java.util.Map;

public record ErrorBaseResponse(
        String generalErrorMessage,
        Map<String, String> errors
) {
}
