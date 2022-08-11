import { FieldBase } from './field-base';

export class TextareaField extends FieldBase<string> {
  controlType = 'textarea';
  type: String;

  constructor(options: {} = {}) {
    super(options);
    this.type = options['type'] || '';
  }
}