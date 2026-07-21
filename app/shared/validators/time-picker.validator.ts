import {FormControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export class TimePickerValidator {

	public static maxTimeAndMinTime(params: any): ValidatorFn | null {

		//"00:00" 24H format
		return (control: FormControl): ValidationErrors | null => {

			let val: { hour: number, minute: number } = control.value;
			let hours = val.hour;
			let minute = val.minute;

			let minTime = params.min;
			let maxTime = params.max;

			if (hours != null && hours != undefined && minute != null && minute != undefined) {
				if (minTime) {

					let minHour = parseInt(minTime.split(":")[0]);
					let minMinute = parseInt(minTime.split(":")[1]);

					if (minHour > hours) {
						return {
							hasMinTimeError: true,
							hasMaxTimeError: false
						};
					} else if (minHour == hours) {
						if (minMinute > minute) {
							return {
								hasMinTimeError: true,
								hasMaxTimeError: false
							};
						}
					}
				}
				if (maxTime) {

					let maxHour = parseInt(maxTime.split(":")[0]);
					let maxMinute = parseInt(maxTime.split(":")[1]);

					if (maxHour < hours) {
						return {
							hasMinTimeError: false,
							hasMaxTimeError: true
						};
					} else if (maxHour == hours) {
						if (maxMinute < minute) {
							return {
								hasMinTimeError: false,
								hasMaxTimeError: true
							};
						}
					}
				}
			}
			return null;
		};
	}
}
