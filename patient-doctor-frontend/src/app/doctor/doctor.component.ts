import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ValidatorService } from '../services/validator.service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.scss']
})
export class DoctorComponent implements OnInit {

  vaccinattionForm: FormGroup = new FormGroup({
    fiksniTelefonLekara: new FormControl({ value: '(021) 641-3876', disabled: true }, [Validators.required, Validators.pattern("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}")]),
    imeLekara: new FormControl({ value: 'Jelena', disabled: true }, [Validators.required,]),
    prezimeLekara: new FormControl({ value: 'Stojanovic', disabled: true }, [Validators.required]),
    zdravstvenaUstanova: new FormControl({ value: 'Promenada', disabled: true }, [Validators.required]),
    punkt: new FormControl({ value: '', disabled: false }, [Validators.required, Validators.pattern("[0-9]+")]),

    
    datumIzdavanjaVakcine: new FormControl({ value: '21.10.2022', disabled: true }, [Validators.required,]),
    nacinDavanjaVakcine: new FormControl({ value: 'IM', disabled: false }, [Validators.required,]),
    ekstremitetVakcine: new FormControl({ value: 'DR', disabled: false }, [Validators.required,]),
    serijaVakcine: new FormControl({ value: '', disabled: false }, [Validators.required, Validators.pattern("[0-9]+")]),
    kontraindikacije: new FormControl({ value: '', disabled: false }, []),
    odlukaKomisije: new FormControl({ value: "NE", disabled: false }, [Validators.required]),

    datumIzdavanja: new FormControl({ value: '21.10.2022', disabled: true }, [Validators.required,]),
    nacinDavanja: new FormControl({ value: 'IM', disabled: true }, [Validators.required,]),
    ekstremitet: new FormControl({ value: 'DR', disabled: true }, [Validators.required,]),

    izabranaVakcina: new FormControl({ value: 'Sinofarm', disabled: true }, [Validators.required,]),
    ime: new FormControl({ value: 'Jovan', disabled: true }, [Validators.required,]),
    prezime: new FormControl({ value: 'Simic', disabled: true }, [Validators.required]),
    imeOca: new FormControl({ value: 'Miladin', disabled: true }, [Validators.required]),
    pol: new FormControl({ value: 'MUSKI', disabled: true }, [Validators.required]),  
    idBroj: new FormControl({ value: '1510999800078', disabled: true }, [Validators.required]),
    drzavljanstvo: new FormControl({ value: 'SRPSKO', disabled: true }, [Validators.required]),
    mobilniTelefon: new FormControl({ value: '0691782912', disabled: true }, [Validators.required, Validators.pattern("[0-9]{10}")]),
    fiksniTelefon: new FormControl({ value: '(021) 641-3876', disabled: true }, [Validators.required, Validators.pattern("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}")]),
  });

  constructor( public validator: ValidatorService) { 
    validator.setForm(this.vaccinattionForm);

  }

  ngOnInit(): void {
  }

}
