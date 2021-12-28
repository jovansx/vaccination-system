package akatsuki.officialsystem.utils.modelmappers;

public interface IModelMapper<T> {

    T convertToObject(String xmlString);

    String convertToXml(T object);
}