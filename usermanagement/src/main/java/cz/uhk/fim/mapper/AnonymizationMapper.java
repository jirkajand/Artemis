package cz.uhk.fim.mapper;

import cz.uhk.fim.entity.InternationalStudentEntity;
import cz.uhk.fim.entity.LocalStudentEntity;
import cz.uhk.fim.entity.enums.AccountStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AnonymizationMapper {

    @Mapping(target = "keycloakId", qualifiedByName = "setAsNull")
    @Mapping(target = "firstName", constant = "ANONYMIZED")
    @Mapping(target = "lastName", constant = "ANONYMIZED")
    @Mapping(target = "email", qualifiedByName = "setAsNull")
    @Mapping(target = "phoneNumber", qualifiedByName = "setAsNull")
    @Mapping(target = "accountStatus", qualifiedByName = "setAccountStatusAnonymized")
    @Mapping(target = "bio", constant = "ANONYMIZED")
    @Mapping(target = "favouriteActivities", qualifiedByName = "setFavouriteActivitiesAsNull")
    @Mapping(target = "isActive", constant = "false")
    @Mapping(target = "homeUniversity", constant = "ANONYMIZED")
    @Mapping(target = "accommodation", constant = "ANONYMIZED")
    @Mapping(target = "anonymizedHasBuddy", source = "assignedBuddy", qualifiedByName = "setAsHasBuddyFlag")
    InternationalStudentEntity anonymizeInternationalStudent(InternationalStudentEntity internationalStudentEntity);

    @Named("setAsNull")
    default String setAsNull(String value) {
        return null;
    }

    @Named("setAsNull")
    default UUID setAsNull(UUID value) {
        return null;
    }

    @Named("setFavouriteActivitiesAsNull")
    default Set<String> setFavouriteActivitiesAsNull(Set<String> value) {
        return new HashSet<>();
    }

    @Named("setAccountStatusAnonymized")
    default AccountStatusEnum setAccountStatusAnonymized(AccountStatusEnum accountStatusEnum) {
        return AccountStatusEnum.ANONYMIZED;
    }

    @Named("setAsNull")
    default LocalStudentEntity setAsNull(LocalStudentEntity value) {
        return null;
    }

    @Named("setAsHasBuddyFlag")
    default Boolean setAsHasBuddyFlag(LocalStudentEntity assignedBuddy) {
        return assignedBuddy != null;
    }
}
