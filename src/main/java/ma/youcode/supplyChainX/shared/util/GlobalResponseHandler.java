//package ma.youcode.supplyChainX.shared.util;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@ControllerAdvice
//public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
//
//    private final LocaleResolver localeResolver;
//
//    public GlobalResponseHandler(LocaleResolver localeResolver) {
//        this.localeResolver = localeResolver;
//    }
//
//    @Override
//    public boolean supports(MethodParameter returnType,
//                            Class<? extends HttpMessageConverter<?>> converterType) {
//        // Apply only if not already a ResponseEntity
//        return !returnType.getParameterType().equals(org.springframework.http.ResponseEntity.class);
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body,
//                                  MethodParameter returnType,
//                                  MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
//                                  ServerHttpRequest request,
//                                  ServerHttpResponse response) {
//
//        // EXCLUDE swagger / actuator / docs endpoints
//        String path = request.getURI().getPath();
//        if (path.startsWith("v3/api-docs") || path.startsWith("swagger-ui") || path.startsWith("/actuator")) {
//            return body; // don't wrap Swagger responses
//        }
//
//        // Already wrapped
//        if (body instanceof ApiResponse) {
//            return body;
//        }
//
//        // Wrap normally
//        return ApiResponse.success(body);
//    }
//}
