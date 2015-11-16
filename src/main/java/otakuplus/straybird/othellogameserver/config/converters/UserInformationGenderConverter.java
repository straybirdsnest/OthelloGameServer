package otakuplus.straybird.othellogameserver.config.converters;

import otakuplus.straybird.othellogameserver.models.UserInformation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserInformationGenderConverter implements AttributeConverter<UserInformation.Gender, String> {
    @Override
    public String convertToDatabaseColumn(UserInformation.Gender gender) {
        switch (gender) {
            case MALE:
                return "MALE";
            case FEMALE:
                return "FEMALE";
            case SECRET:
                return "SECRET";
            default:
                throw new IllegalArgumentException("unknown gender " + gender);
        }
    }

    @Override
    public UserInformation.Gender convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "MALE":
                return UserInformation.Gender.MALE;
            case "FEMALE":
                return UserInformation.Gender.FEMALE;
            case "SECRET":
                return UserInformation.Gender.SECRET;
            default:
                throw new IllegalArgumentException("unknown value for gender " + dbData);
        }
    }
}
