<script>
	import Autocomplete from "@smui-extra/autocomplete";
    import { countryIsoCodes } from "$lib/assets/country-iso-codes";

    let { value = $bindable(), ...rest } = $props();
</script>


<Autocomplete bind:value={value} {...rest} options={countryIsoCodes} />