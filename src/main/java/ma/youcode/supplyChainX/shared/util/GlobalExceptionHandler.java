//package ma.youcode.supplyChainX.shared.util;
//import ma.youcode.supplyChainX.shared.exception.BadRequestException;
//import ma.youcode.supplyChainX.shared.exception.ResourceNotFoundException;
//import ma.youcode.supplyChainX.shared.exception.UnauthorizedException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // 404 - Resource not found
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ApiResponse.error(ex.getMessage()));
//    }
//
//    // 400 - Bad request (invalid data, etc.)
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.error(ex.getMessage()));
//    }
//
//    // 401 - Unauthorized
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<ApiResponse<?>> handleUnauthorized(UnauthorizedException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.error(ex.getMessage()));
//    }
//
//    // 400 - Validation errors (e.g. @Valid fails)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.error("Validation failed", errors));
//    }
//
//    // 500 - Uncaught exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
//        ex.printStackTrace(); // for debugging in console
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.error("Internal server error: " + ex.getMessage()));
//    }
//}