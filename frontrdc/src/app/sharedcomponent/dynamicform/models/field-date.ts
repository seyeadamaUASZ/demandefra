import { FieldBase } from './field-base';

export class DateField extends FieldBase<string> {
  controlType = 'date';
  type: Date;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}