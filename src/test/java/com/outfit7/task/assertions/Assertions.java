package com.outfit7.task.assertions;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {

    public static final String ALL_BREEDS_EXPECTED_LIST = "AllBreedsMessage(affenpinscher=[], african=[], airedale=[], akita=[], appenzeller=[], australian=[shepherd], basenji=[], beagle=[], bluetick=[], borzoi=[], bouvier=[], boxer=[], brabancon=[], briard=[], buhund=[norwegian], bulldog=[boston, english, french], bullterrier=[staffordshire], cattledog=[australian], chihuahua=[], chow=[], clumber=[], cockapoo=[], collie=[border], coonhound=[], corgi=[cardigan], cotondetulear=[], dachshund=[], dalmatian=[], dane=[great], deerhound=[scottish], dhole=[], dingo=[], doberman=[], elkhound=[norwegian], entlebucher=[], eskimo=[], finnish=[lapphund], frise=[bichon], germanshepherd=[], greyhound=[italian], groenendael=[], havanese=[], hound=[afghan, basset, blood, english, ibizan, plott, walker], husky=[], keeshond=[], kelpie=[], komondor=[], kuvasz=[], labradoodle=[], labrador=[], leonberg=[], lhasa=[], malamute=[], malinois=[], maltese=[], mastiff=[bull, english, tibetan], mexicanhairless=[], mix=[], mountain=[bernese, swiss], newfoundland=[], otterhound=[], ovcharka=[caucasian], papillon=[], pekinese=[], pembroke=[], pinscher=[miniature], pitbull=[], pointer=[german, germanlonghair], pomeranian=[], poodle=[medium, miniature, standard, toy], pug=[], puggle=[], pyrenees=[], redbone=[], retriever=[chesapeake, curly, flatcoated, golden], ridgeback=[rhodesian], rottweiler=[], saluki=[], samoyed=[], schipperke=[], schnauzer=[giant, miniature], setter=[english, gordon, irish], sharpei=[], sheepdog=[english, shetland], shiba=[], shihtzu=[], spaniel=[blenheim, brittany, cocker, irish, japanese, sussex, welsh], springer=[english], stbernard=[], terrier=[american, australian, bedlington, border, cairn, dandie, fox, irish, kerryblue, lakeland, norfolk, norwich, patterdale, russell, scottish, sealyham, silky, tibetan, toy, welsh, westhighland, wheaten, yorkshire], tervuren=[], vizsla=[], waterdog=[spanish], weimaraner=[], whippet=[], wolfhound=[irish])";
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    public static final String IMAGE_URL = "https://images.dog.ceo/breeds/";
    public static final String BREED_NOT_FOUND_MESSAGE = "Breed not found (master breed does not exist)";
    public static final String SUB_BREED_NOT_FOUND_MESSAGE = "Breed not found (sub breed does not exist)";

    public static void checkStatusCode(int actualCode, int expectedCode) {
        assertThat(actualCode)
                .withFailMessage(String.format("Actual status code is: %s, but expected is: %s", actualCode, expectedCode))
                .isEqualTo(expectedCode);
    }

    public static void checkResponseStatusIsSuccess(String actualStatus) {
        assertThat(actualStatus)
                .withFailMessage(String.format("Actual status message is: %s, but expected: %s", actualStatus, SUCCESS_STATUS))
                .isEqualTo(SUCCESS_STATUS);
    }

    public static void checkResponseStatusIsError(String actualStatus) {
        assertThat(actualStatus)
                .withFailMessage(String.format("Actual status message is: %s, but expected: %s", actualStatus, ERROR_STATUS))
                .isEqualTo(ERROR_STATUS);
    }
}
