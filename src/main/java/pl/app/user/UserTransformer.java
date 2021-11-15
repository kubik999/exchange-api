package pl.app.user;

import pl.app.entity.AppUser;

class UserTransformer {

    public static AppUser toDomain(AppUserDto userDto) {

        AppUser appUser = new AppUser();
        appUser.setPesel(userDto.getPesel());
        appUser.setName(userDto.getName());
        appUser.setLastName(userDto.getLastName());
        appUser.setBirthDate(userDto.getBirthDate());
        appUser.setAccountBalance(userDto.getAccountBalance());
        appUser.setPassword(userDto.getPassword());
        return appUser;
    }
}
