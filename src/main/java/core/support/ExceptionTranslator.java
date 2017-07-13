package core.support;

public interface ExceptionTranslator {
    ExceptionDefinition translate(Throwable e);
}
