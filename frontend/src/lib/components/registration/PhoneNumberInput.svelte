<script lang="ts">
	import { countryPhoneCodes, type CountryPhoneCode } from "$lib/assets/country-phone-codes";
	import Autocomplete from "@smui-extra/autocomplete";
	import Textfield from "@smui/textfield";
    import HelperText from '@smui/textfield/helper-text';

    let { value = $bindable(), ...rest } = $props();

    let options = $state(countryPhoneCodes);
    let selectedPhoneCodeObj: CountryPhoneCode | undefined = $state();
    let selectedPhoneNumber: string = $state('');

    $effect(() => {
        if (selectedPhoneCodeObj && selectedPhoneNumber) {
            value = `${selectedPhoneCodeObj.dial_code} ${selectedPhoneNumber}`;
        }
    });


</script>

<div class="phone-input">
    <Autocomplete
        class="country-code-autocomplete"
        options={options}
        getOptionLabel={(option: CountryPhoneCode) => option ? `${option.dial_code} (${option.code})` : ''}
        bind:value={selectedPhoneCodeObj}
        label="Code"
    />
    <Textfield class="phone-number-textfield" bind:value={selectedPhoneNumber} label="Phone Number" {...rest} />
</div>

<style>
    .phone-input {
        display: flex;
        gap: 0;
        align-items: center;
    }

    .phone-input :global(.country-code-autocomplete) {
        flex: 1;
        min-width: 90px;
    }

    .phone-input :global(.phone-number-textfield) {
        flex: 2;
    }
</style>