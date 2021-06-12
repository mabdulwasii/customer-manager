import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import profile, {
  ProfileState
} from 'app/entities/profile/profile.reducer';
// prettier-ignore
import serviceCategory, {
  ServiceCategoryState
} from 'app/entities/service-category/service-category.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import review, {
  ReviewState
} from 'app/entities/review/review.reducer';
// prettier-ignore
import payment, {
  PaymentState
} from 'app/entities/payment/payment.reducer';
// prettier-ignore
import service, {
  ServiceState
} from 'app/entities/service/service.reducer';
// prettier-ignore
import hardware, {
  HardwareState
} from 'app/entities/hardware/hardware.reducer';
// prettier-ignore
import training, {
  TrainingState
} from 'app/entities/training/training.reducer';
// prettier-ignore
import software, {
  SoftwareState
} from 'app/entities/software/software.reducer';
// prettier-ignore
import services, {
  ServicesState
} from 'app/entities/services/services.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly profile: ProfileState;
  readonly serviceCategory: ServiceCategoryState;
  readonly address: AddressState;
  readonly review: ReviewState;
  readonly payment: PaymentState;
  readonly service: ServiceState;
  readonly hardware: HardwareState;
  readonly training: TrainingState;
  readonly software: SoftwareState;
  readonly services: ServicesState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  profile,
  serviceCategory,
  address,
  review,
  payment,
  service,
  hardware,
  training,
  software,
  services,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
