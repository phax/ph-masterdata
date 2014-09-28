/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.masterdata.currency;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.DevelopersNote;
import com.helger.commons.annotations.Translatable;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.text.impl.TextProvider;
import com.helger.commons.text.resolve.DefaultTextResolver;

/**
 * Contains the names of all currencies. Accessed by {@link ECurrency}.
 *
 * @author Philip Helger
 */
@Translatable
public enum ECurrencyName implements IHasDisplayText
{
  AED ("VAE Dirham", "Emirati Dirham"),
  AFN ("Afghani", "Afghanistan Afghani"),
  ALL ("Lek", "Albanian Lek"),
  AMD ("Dram", "Armenian Dram"),
  ANG ("Antillen-Gulden", "Dutch Guilder"),
  AOA ("Kwanza", "Angolan Kwanza"),
  ARS ("Argentinischer Peso", "Argentine Peso"),
  AZN ("Aserbaidschan-Manat", "Azerbaijani Manat"),
  AUD ("Australischer Dollar", "Australian Dollar"),
  AWG ("Aruba-Florin", "Aruban or Dutch Guilder"),
  BAM ("Konvertible Mark", "Bosnia-Herzegovina Convertible Mark"),
  BBD ("Barbados-Dollar", "Barbadian or Bajan Dollar"),
  BDT ("Taka", "Bangladeshi Taka"),
  BGN ("Lew", "Bulgarian Lev"),
  BHD ("Bahrain-Dinar", "Bahraini Dinar"),
  BIF ("Burundi-Franc", "Burundian Franc"),
  BMD ("Bermuda-Dollar", "Bermudian Dollar"),
  BND ("Brunei-Dollar", "Bruneian Dollar"),
  BOB ("Boliviano", "Bolivian Boliviano"),
  BRL ("Brasilianischer Real", "Brazilian Real"),
  BSD ("Bahama-Dollar", "Bahamian Dollar"),
  BTN ("Ngultrum", "Bhutanese Ngultrum"),
  BWP ("Pula", "Botswana Pula"),
  BYR ("Weißrussischer Rubel", "Belarusian Ruble"),
  BZD ("Belize-Dollar", "Belizean Dollar"),
  CAD ("Kanadischer Dollar", "Canadian Dollar"),
  CDF ("Kongo-Franc", "Congolese Franc"),
  CHF ("Schweizer Franken", "Swiss Franc"),
  CLP ("Chilenischer Peso", "Chilean Peso"),
  CNY ("Remnibi Yuan", "Chinese Yuan Renminbi"),
  COP ("Kolumbianischer Peso", "Colombian Peso"),
  CRC ("Costa-Rica Colón", "Costa Rican Colón"),
  @Deprecated
  CSD ("Serbischer Dinar", "Serbian Dinar"),
  CUC ("Peso concertible", "Cuban Convertible Peso"),
  CUP ("Kubanischer Peso", "Cuban Peso"),
  CVE ("Kap-Verde-Escudo", "Cape Verdean Escudo"),
  CZK ("Tschechische Kronen", "Czech Koruna"),
  DJF ("Dschibuti-Franc", "Djiboutian Franc"),
  DKK ("Dänische Kronen", "Danish Krone"),
  DOP ("Dominikanischer Peso", "Dominican Peso"),
  DZD ("Algerischer Dinar", "Algerian Dinar"),
  @Deprecated
  @DevelopersNote ("Was replaced with Euro per 1.1.2011")
  EEK ("Estnische Krone", "Estonian Kroon"),
  EGP ("Ägyptisches Pfund", "Egyptian Pound"),
  ERN ("Nakfa", "Eritrean Nakfa"),
  ETB ("Birr", "Ethiopian Birr"),
  EUR ("Euro", "Euro"),
  FJD ("Fidschi-Dollar", "Fijian Dollar"),
  FKP ("Falkland-Pfund", "Falkland Island Pound"),
  GBP ("Pfund Sterling", "Pound Sterling"),
  GEL ("Lari", "Georgian Lari"),
  GHS ("Ghanaischer Cedi", "Ghanaian Cedi"),
  GIP ("Gibraltar-Pfund", "Gibraltar Pound"),
  GMD ("Dalasi", "Gambian Dalasi"),
  GNF ("Guinea-Franc", "Guinean Franc"),
  GTQ ("Quetzal", "Guatemalan Quetzal"),
  GYD ("Guyana-Dollar", "Guyanese Dollar"),
  HKD ("Hongkong-Dollar", "Hong Kong Dollar"),
  HNL ("Lempira", "Honduran Lempira"),
  HRK ("Kuna", "Croatian Kuna"),
  HTG ("Gourde", "Haitian Gourde"),
  HUF ("Forint", "Hungarian Forint"),
  IDR ("Indonesische Rupiah", "Indonesian Rupiah"),
  ILS ("Neuer Schekel", "Israeli Shekel"),
  INR ("Indische Rupie", "Indian Rupee"),
  IQD ("Irakischer Dinar", "Iraqi Dinar"),
  IRR ("Iranischer Rial", "Iranian Rial"),
  ISK ("Isländische Krone", "Icelandic Krona"),
  JMD ("Jamaikanischer Dollar", "Jamaican Dollar"),
  JOD ("Jordanischer Dinar", "Jordanian Dinar"),
  JPY ("Yen", "Japanese Yen"),
  KES ("Kenia-Schilling", "Kenyan Shilling"),
  KGS ("Som", "Kyrgyzstani Som"),
  KHR ("Riel", "Cambodian Riel"),
  KMF ("Komoren-Franc", "Comoran Franc"),
  KPW ("Nordkoreanischer Won", "North Korean Won"),
  KRW ("Südkoreanischer Won", "South Korean Won"),
  KWD ("Kuwait-Dinar", "Kuwaiti Dinar"),
  KYD ("Kaiman-Dollar", "Caymanian Dollar"),
  KZT ("Tenge", "Kazakhstani Tenge"),
  LAK ("Kip", "Lao or Laotian Kip"),
  LBP ("Libanesisches Pfund", "Lebanese Pound"),
  LKR ("Sri Lanka-Rupie", "Sri Lankan Rupee"),
  LRD ("Liberianischer Dollar", "Liberian Dollar"),
  LSL ("Loti", "Basotho Loti"),
  LTL ("Litas", "Lithuanian Litas"),
  LVL ("Lats", "Latvian Lats"),
  LYD ("Libyscher Dinar", "Libyan Dinar"),
  MAD ("Marokkanischer Dirham", "Moroccan Dirham"),
  MDL ("Moldawischer Leu", "Moldovan Leu"),
  MGA ("Ariary", "Malagasy Ariary"),
  MKD ("Mazedonischer Denar", "Macedonia Denar"),
  MMK ("Kyat", "Burmese Kyat"),
  MNT ("Tugrik", "Mongolian Tughrik"),
  MOP ("Pataca", "Macau Pataca"),
  MRO ("Ouguiya", "Mauritanian Ouguiya"),
  MUR ("Mauritius-Rupie", "Mauritian Rupee"),
  MVR ("Rufiyaa", "Maldivian Rufiyaa"),
  MWK ("Malawi-Kwacha", "Malawian Kwacha"),
  MXN ("Mexikanischer Peso", "Mexican Peso"),
  MYR ("Ringgit", "Malaysian Ringgit"),
  MZN ("Metical", "Mozambican Metical"),
  NAD ("Namibischer Dollar", "Namibian Dollar"),
  NGN ("Naira", "Nigerian Naira"),
  NIO ("Córdoba Oro", "Nicaraguan Cordoba"),
  NOK ("Norwegische Krone", "Norwegian Krone"),
  NPR ("Nepalesische Rupie", "Nepalese Rupee"),
  NZD ("Neuseeland-Dollar", "New Zealand Dollar"),
  OMR ("Omanischer Rial", "Omani Rial"),
  PAB ("Balboa", "Panamanian Balboa"),
  PEN ("Nuevo Sol", "Peruvian Nuevo Sol"),
  PGK ("Kina", "Papua New Guinean Kina"),
  PHP ("Philippinischer Peso", "Philippine Peso"),
  PKR ("Pakistanische Rupie", "Pakistani Rupee"),
  PLN ("Złoty", "Polish Złoty"),
  PYG ("Guarani", "Paraguayan Guarani"),
  QAR ("Katar-Riyal", "Qatari Riyal"),
  RON ("Rumänischer Leu", "Romanian Leu"),
  RSD ("Serbischer Dinar", "Serbian dinar"),
  RUB ("Russische Rubel", "Russian Ruble"),
  RWF ("Ruanda-Franc", "Rwandan Franc"),
  SAR ("Saudi-Rial", "Saudi Arabian Riyal"),
  SBD ("Salomonen-Dollar", "Solomon Islander Dollar"),
  SCR ("Seychellen-Rupie", "Seychellois Rupee"),
  SDG ("Sudanesisches Pfund", "Sudanese Pound"),
  SEK ("Schwedische Kronen", "Swedish Krona"),
  SGD ("Singapur-Dollar", "Singapore Dollar"),
  SHP ("St.-Helena-Pfund", "Saint Helenian Pound"),
  SLL ("Leone", "Sierra Leonean Leone"),
  SOS ("Somalia-Schilling", "Somali Shilling"),
  SRD ("Suriname-Dollar", "Surinamese Dollar"),
  SSP ("Südsudanesisches Pfund", "South Sudan Pound"),
  STD ("Dobra", "Sao Tomean Dobra"),
  SVC ("Colon", "Salvadoran Colon"),
  SYP ("Syrisches Pfund", "Syrian Pound"),
  SZL ("Lilangeni", "Swazi Lilangeni"),
  THB ("Baht", "Thai Baht"),
  TJS ("Somoni", "Tajikistani Somoni"),
  TMT ("Turkmenistan-Manat", "Turkmenistani Manat"),
  TND ("Tunesischer Dinar", "Tunisian Dinar"),
  TOP ("Pa'anga", "Tongan Pa'anga"),
  TRY ("Neue Lira", "New Turkish Lira"),
  TTD ("Trinidad und Tobago Dollar", "Trinidadian Dollar"),
  TWD ("Taiwan-Dollar", "Taiwan New Dollar"),
  TZS ("Tansania-Schilling", "Tanzanian Shilling"),
  UAH ("Hrywna", "Ukrainian Hryvnia"),
  UGX ("Uganda-Schilling", "Ugandan Shilling"),
  USD ("US-Dollar", "United States Dollar"),
  UYU ("Uruguayischer Peso", "Uruguayan Peso"),
  UZS ("So'm", "Uzbekistani Som"),
  VEF ("Bolívar fuerte", "Venezuelan Bolivar"),
  VND ("Dong", "Vietnamese Dong"),
  VUV ("Vatu", "Ni-Vanuatu Vatu"),
  WST ("Tala", "Samoan Tala"),
  XAF ("CFA-Franc BEAC", "CFA Franc BEAC"),
  XCD ("Ostkaribischer Dollar", "East Caribbean Dollar"),
  XOF ("CFA-Franc BCEAO", "CFA Franc"),
  XPF ("CFP-Franc", "CFP Franc"),
  YER ("Jemen-Rial", "Yemeni Rial"),
  ZAR ("Rand", "South African Rand"),
  @Deprecated
  ZMK ("Kwacha", "Zambian Kwacha"),
  ZMW ("Neuer Kwacha", "New Zambian Kwacha"),
  ZWD ("Simbabwe-Dollar", "Zimbabwean Dollar"),
  @Deprecated
  ZWL ("Simbabwe-Dollar", "Zimbabwean Dollar");

  private final TextProvider m_aTP;

  private ECurrencyName (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextProvider.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
  }
}
