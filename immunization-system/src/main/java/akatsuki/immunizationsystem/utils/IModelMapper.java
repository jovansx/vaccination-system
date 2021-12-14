package akatsuki.immunizationsystem.utils;

public interface IModelMapper<T> {

    T convertToObject(String xmlString);

    String convertToXml(T object);
}