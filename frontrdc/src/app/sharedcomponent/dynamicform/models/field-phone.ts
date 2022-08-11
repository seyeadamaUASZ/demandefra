import { FieldBase } from './field-base';

export class PhoneField extends FieldBase<string> {
  controlType ='phone';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}