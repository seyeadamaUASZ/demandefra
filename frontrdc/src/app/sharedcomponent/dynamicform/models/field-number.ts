import { FieldBase } from './field-base';

export class NumberField extends FieldBase<string> {
  controlType = 'number';
  type: number;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}